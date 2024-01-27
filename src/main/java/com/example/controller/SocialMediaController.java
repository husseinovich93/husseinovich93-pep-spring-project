package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

 @RestController
public class SocialMediaController {

    @Autowired
    AccountService accountService;
    List<Account> accounts = new ArrayList<>();

    @Autowired
    MessageService messageService;
    List<Message> messages = new ArrayList<>();


    public SocialMediaController() {
        this.accountService = new AccountService(null);
        this.messageService = new MessageService(null);
    }
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

        //## 1: Our API should be able to process new User registrations.

    @PostMapping("register")
    private ResponseEntity<Account> registerAccount(@RequestBody Account account){
        accounts = accountService.getAllAccounts();
        int counter = 0;

        for(Account acc : accounts){
            if(acc.getUsername().equals(account.getUsername())){
                counter++;
                return ResponseEntity.status(409).body(account);
                }
            }
            if((account.getUsername().isEmpty() == false) && (account.getPassword().length() >=4) && (counter == 0)){
                 return ResponseEntity.status(200).body(accountService.addAccount(account));
            }else{
                return ResponseEntity.status(400).body(account);
            }
            
    }


    //## 2: Our API should be able to process User logins.
    @PostMapping("login")
    private ResponseEntity<Account> loginAccount(@RequestBody Account account){

        int checker = 0;
        accounts = accountService.getAllAccounts();
        for(Account acc : accounts){
            if((acc.getUsername().equals(account.getUsername())) && (acc.getPassword().equals(account.getPassword()))){
                checker++;
                return ResponseEntity.status(200).body(acc);
            }
        }
        if(checker == 0){
            return ResponseEntity.status(401).body(account);
        }
        return null;
    }


    //## 3: Our API should be able to process the creation of new messages.
    @PostMapping("messages")
    private ResponseEntity<Message> addMessage(@RequestBody Message message){
            
        accounts = accountService.getAllAccounts();
        for(Account acc:accounts){    
            if((int)acc.getAccount_id() == (int)message.getPosted_by()){
                if((message.getMessage_text().isEmpty()== false) && (message.getMessage_text().length()<=255)){
                    return ResponseEntity.status(200).body(messageService.createMessage(message));                    }
            }
        }
        System.out.println();
        return ResponseEntity.status(400).body(null);
    }

    //## 4: Our API should be able to retrieve all messages.

    @GetMapping("messages")
    private ResponseEntity<List<Message>> viewAllMessages(){
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    //## 5: Our API should be able to retrieve a message by its ID.

    @GetMapping("messages/{message_id}")
    private ResponseEntity<Message>viewMessageById(@PathVariable int message_id){

        
        Message message = messageService.getMessageById(message_id);
        for(Message msg : messageService.getAllMessages()){
            if(message_id == msg.getMessage_id()){
                return ResponseEntity.status(200).body(message);
            }
        }
        return null;
        

    }

    //## 6: Our API should be able to delete a message identified by a message ID.

    @DeleteMapping("messages/{message_id}")
    private ResponseEntity<Integer>delMessageById(@PathVariable int message_id){

        
        for(Message msg:messageService.getAllMessages()){
            if(message_id == msg.getMessage_id()){
                messageService.delMessageByID(message_id);
                return ResponseEntity.status(200).body(1);
            }
        }
        return null;

    }

    //## 7: Our API should be able to update a message text identified by a message ID.

    @PatchMapping("messages/{message_id}")
    private ResponseEntity<Integer>editMessageById(@PathVariable int message_id, @RequestBody Message message ){

        for(Message msg : messageService.getAllMessages()){
            if((msg.getMessage_id() == message_id) && (message.getMessage_text().isEmpty()==false) && (message.getMessage_text().length()<=255)){
                //System.out.println("Hello This is a Match"+(msg.getMessage_id()+".....>"+message_id));
                messageService.updateMessageByID(msg.getMessage_id(), message);
                return ResponseEntity.status(200).body(1);
            }
        }
        return ResponseEntity.status(400).body(null);
    }


    //## 8: Our API should be able to retrieve all messages written by a particular user.

    @GetMapping("accounts/{account_id}/messages")
    public ResponseEntity <List<Message>>viewAllMessagesByUserID(@PathVariable int account_id){
        
        List<Message> userMessages = new ArrayList<>();
        for(Message msg : messageService.getAllMessages()){
            if(msg.getPosted_by() == account_id){
                userMessages.add(msg);
            }
        }
        return ResponseEntity.status(200).body(userMessages);
        
    }

}
