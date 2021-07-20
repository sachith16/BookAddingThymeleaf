package com.example.books;

import com.google.gson.Gson;
import org.json.simple.parser.JSONParser;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class BookController {

    @GetMapping
    public String viewBooks(Model model) {
        model.addAttribute("books", getBooks());
        return "view-books";
    }

    @GetMapping("/loginView")
    public String loginView(Model model) {
        return "login";
    }

    @GetMapping("/addBookView")
    public String addBookView(Model model, HttpSession session) {
        if(session.getAttribute("email")==null){
            return "login";
        }
        return "add-book";
    }

    @RequestMapping(value = "/addBook", method = RequestMethod.POST)
    public ResponseEntity<Response> addBook(@ModelAttribute("book") Book book, HttpServletRequest request) {
        book.setBookId(String.valueOf(getBooks().size()+1));
        book.setAddedBy(request.getSession().getAttribute("userId").toString());
        try {
            writeBooktoFile(book);
        } catch (IOException e) {
            return ResponseEntity.ok().body(new Response(e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response("Successfully Added"));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Response> login(@ModelAttribute("user") User user, HttpServletRequest request) {
        for (User existingUser:getUsers()) {
            if(user.getEmail().equals(existingUser.getEmail()) && user.getPassword().equals(existingUser.getPassword())){
                request.getSession().setAttribute("email", user.getEmail());
                request.getSession().setAttribute("userId", existingUser.getUserId());
                return ResponseEntity.ok().body(new Response("Login Successfully"));
            }else if(user.getEmail().equals(existingUser.getEmail()) && !user.getPassword().equals(existingUser.getPassword())){
                return ResponseEntity.ok().body(new Response("Invalid Password"));
            }
        }
        return ResponseEntity.ok().body(new Response("User Not Exists"));
    }

    public ArrayList<Book> getBooks(){
        ArrayList<Book> books=new ArrayList<>();
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("book-list.json"))
        {
            Object obj = jsonParser.parse(reader);

            JSONArray bookList = (JSONArray) obj;
            Book booksArray[] = new Gson().fromJson(bookList.toString(), Book[].class);
            for (Book book:booksArray) {
                books.add(book);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return books;
    }

    public ArrayList<User> getUsers(){
        ArrayList<User> users=new ArrayList<>();
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("user-list.json"))
        {
            Object obj = jsonParser.parse(reader);

            JSONArray userList = (JSONArray) obj;
            User userArray[] = new Gson().fromJson(userList.toString(), User[].class);
            for (User user:userArray) {
                users.add(user);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return users;
    }

    private void parseBookObject(JSONObject book, ArrayList<Book> books)
    {
        String bookId= (String) book.get("bookId");
        String bookName= (String) book.get("bookName");
        String copyCount= (String) book.get("copyCount");
        String author= (String) book.get("author");
        String coverImage= (String) book.get("coverImage");
        String addedBy= (String) book.get("addedBy");

        books.add(new Book(bookId,bookName,copyCount,author,coverImage,addedBy));
    }

    public void writeBooktoFile(Book book) throws IOException{
        ArrayList<Book> books=getBooks();
        books.add(book);
        FileWriter file = new FileWriter("book-list.json");
        file.write(new Gson().toJson(books));
        file.flush();
    }

}