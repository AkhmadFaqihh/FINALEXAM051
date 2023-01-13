/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.b.FINALEXAM;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.ArrayList;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Akhmad Faqih 20200140051
 */
@RestController
@CrossOrigin
@RequestMapping("/data")
public class Controller {
    
    Person mydata = new Person();
    PersonJpaController ctrl = new PersonJpaController();
    
    @GetMapping("/{id}")
    public Person getNameById(@PathVariable("id") String id){
        try {
            mydata = ctrl.findPerson(Integer.parseInt(id));
        } catch (Exception e){
        }
        return mydata;
    }
    
    @GetMapping
    public List<Person> getAll() {
        List<Person> dummy = new ArrayList<>();
        try {
            dummy = ctrl.findPersonEntities();
        } catch (Exception e) {
            dummy = List.of();
        }
        return dummy;
    }
    @PostMapping
    public String createData (HttpEntity<String> paket){
        String message = "Data Saved";
        
        try {
            String json_receive = paket.getBody();
            
            ObjectMapper map = new ObjectMapper();
            
            Person data = new Person();
            
            data = map.readValue(json_receive, Person.class);
            
            ctrl.create(data);
            message = data.getNama() + "  Data Saved";
            
        }catch (Exception e) {message = "Failed";}
        
        
        return message;
    }
    
    @PutMapping
    public String updateData (HttpEntity<String> kiriman) {
    String message = "no action";
    try {
        String json_receive = kiriman.getBody();
        ObjectMapper mapper = new ObjectMapper();
        
        Person newdatas = new Person();
        
        newdatas = mapper.readValue(json_receive, Person.class);
        ctrl.edit(newdatas);
        message = newdatas.getNama() + "has been Updated";
    } catch (Exception e) {
        message = "Failed to update the data";
    }
    return message;
    
    }
    
    @DeleteMapping
    public String deleteData (HttpEntity<String> kiriman) {
    String message = "no action";
    try {
        String json_receive = kiriman.getBody();
        ObjectMapper mapper = new ObjectMapper();
        
        Person newdatas = new Person();
        
        newdatas = mapper.readValue(json_receive, Person.class);
        ctrl.destroy(newdatas.getId());
        message = newdatas.getNama() + "has been deleted";
    } catch (Exception e) {
    }
    return message;    
    }
    
    
    
}
