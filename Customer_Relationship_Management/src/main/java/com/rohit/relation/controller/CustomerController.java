package com.rohit.relation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.rohit.relation.entity.Customer;
import com.rohit.relation.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	// add mapping for "/list"

	@RequestMapping("/list")
	public String listBooks(Model theModel) {

		// get Books from db
		List<Customer> theCustomer = customerService.findAll();

		// add to the spring model
		theModel.addAttribute("Customer", theCustomer);

		return "list-Customer";
	}

	@RequestMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {

		// create model attribute to bind form data
		Customer theCustomer = new Customer();

		theModel.addAttribute("Customer", theCustomer);

		return "Customer-form";
	}
	
	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam(value = "id", required = false) int theid, Model theModel) {

		// get the Book from the service
		Customer theCustomer =  customerService.findById(theid);

		// set Book as a model attribute to per-populate the form
		theModel.addAttribute("Customer", theCustomer);

		// send over to our form
		return "Customer-form";
	}
	       
	@PostMapping("/save")
	public String saveCustomer(@RequestParam(value = "id", required = false) int theid, @RequestParam("FirstName") String firstName,
			@RequestParam("LastName") String lastName, @RequestParam("Email") String email) {

		System.out.println(theid);
		Customer theCustomer;
		if (theid != 0) {
			theCustomer = customerService.findById(theid);
			theCustomer.setFirstName(firstName);
			theCustomer.setLastName(firstName);
			theCustomer.setEmail(email);
		} else
			theCustomer= new Customer(firstName, firstName, email);
		// save the Book
		customerService.save(theCustomer);

		// use a redirect to prevent duplicate submissions
		return "redirect:/customer/list";

	}
	
	@RequestMapping("/delete")
	public String delete(@RequestParam(value = "id", required = false) int theid) {

		// delete the Book
		customerService.deleteById(theid);

		// redirect to /Books/list
		return "redirect:/books/list";

	}


}
