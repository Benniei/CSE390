//
//  AssignmentsViewController.swift
//  Planner
//
//  Created by Bennie Chen on 6/17/21.
//

/*
 Name: Bennie Chen
 ID: 112737201
 CSE 390: Mobile App Development
 Homework 2
 */

import UIKit
import CoreData

class AssignmentsViewController: UIViewController, UITextFieldDelegate {
    
    @IBOutlet weak var assignmentEntry: UITextField!
    @IBOutlet weak var categoryEntry: UITextField!
    @IBOutlet weak var notesEntry: UITextField!
    @IBOutlet weak var datePicker: UIDatePicker!
    @IBOutlet weak var editSwitch: UISegmentedControl!
    
    var currentEvent: Task?
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        if currentEvent != nil {
            assignmentEntry.text = currentEvent!.assignment
            categoryEntry.text = currentEvent!.category
            notesEntry.text = currentEvent!.notes
            datePicker.date = (currentEvent?.date)!
        }
        
        self.changeEditMode(self)
        let textFields: [UITextField] = [assignmentEntry, categoryEntry, notesEntry]
        for textfield in textFields{
            textfield.addTarget(self, action: #selector(UITextFieldDelegate.textFieldShouldEndEditing(_:)), for: UIControl.Event.editingDidEnd)
        }
        // Do any additional setup after loading the view.
    }
    
    func textFieldShouldEndEditing(_ textField: UITextField) -> Bool {
        if currentEvent == nil{
            let context = appDelegate.persistentContainer.viewContext
            currentEvent = Task(context: context)
        }
        currentEvent?.assignment = assignmentEntry.text?.trimmingCharacters(in: .whitespaces)
        currentEvent?.category = categoryEntry.text?.trimmingCharacters(in: .whitespaces)
        currentEvent?.notes = notesEntry.text?.trimmingCharacters(in: .whitespaces)
        return true
    }
    
    @objc func saveTask() {
        currentEvent?.notes = notesEntry.text
        currentEvent?.date = datePicker.date
        appDelegate.saveContext()
        editSwitch.selectedSegmentIndex = 0
        changeEditMode(self)
    }
    
    @IBAction func changeEditMode(_ sender: Any) {
        let textFields: [UITextField] =  [assignmentEntry, categoryEntry, notesEntry]
        if editSwitch.selectedSegmentIndex == 0 {
            for textField in textFields{
                textField.isEnabled = false
                textField.borderStyle = UITextField.BorderStyle.none
            }
            datePicker.isEnabled = false
            navigationItem.rightBarButtonItem = nil
        }
        else if editSwitch.selectedSegmentIndex == 1 {
            for textField in textFields {
                textField.isEnabled = true
                textField.borderStyle = UITextField.BorderStyle.roundedRect
            }
            datePicker.isEnabled = true
            // Creates the Save button on the right half of the navigation bar
            navigationItem.rightBarButtonItem = UIBarButtonItem(barButtonSystemItem: .save, target: self, action: #selector(self.saveTask))
        }
            
    }
    
    
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
