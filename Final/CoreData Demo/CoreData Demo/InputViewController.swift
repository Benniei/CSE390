//
//  InputViewController.swift
//  CoreData Demo
//
//  Created by Bennie Chen on 6/25/21.
//

import UIKit
import CoreData

class InputViewController: UIViewController, UITextFieldDelegate  {
    
    @IBOutlet weak var idView: UITextField!
    @IBOutlet weak var nameView: UITextField!
    @IBOutlet weak var sizeView: UITextField!
    @IBOutlet weak var professorView: UITextField!
    var currentCourse: Course?
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        if currentCourse != nil {
            idView.text = currentCourse?.id
            nameView.text = currentCourse?.name
            professorView.text = currentCourse?.professor
            sizeView.text = currentCourse?.size
        }
        else{
            let context = appDelegate.persistentContainer.viewContext
            currentCourse = Course(context: context)
        }
        
        navigationItem.rightBarButtonItem = UIBarButtonItem(barButtonSystemItem: .save, target: self, action: #selector(self.saveContact))
    }
    
    @objc func saveContact() {
        currentCourse?.id = idView.text
        currentCourse?.name = nameView.text
        currentCourse?.size = sizeView.text
        currentCourse?.professor = professorView.text
        appDelegate.saveContext()
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
