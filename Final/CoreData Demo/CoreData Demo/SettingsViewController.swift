//
//  SettingsViewController.swift
//  CoreData Demo
//
//  Created by Bennie Chen on 6/25/21.
//

import UIKit

class SettingsViewController: UIViewController, UIPickerViewDelegate, UIPickerViewDataSource {

    @IBOutlet weak var pckSortField: UIPickerView!
    @IBOutlet weak var swAscending: UISwitch!
    
    let sortOrderItems: Array<String> = ["ID", "Size", "Professor"]
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        pckSortField.dataSource = self
        pckSortField.delegate = self
    }
    
    override func viewWillAppear(_ animated: Bool) {
        let settings = UserDefaults.standard
        swAscending.setOn(settings.bool(forKey: Constants.kSortDirectionAscending), animated: true)
        let sortField = settings.string(forKey: Constants.kSortField)
        var i = 0
        for field in sortOrderItems {
            if(field == sortField){
                pckSortField.selectRow(i, inComponent: 0, animated: true)
            }
            i += 1
        }
        pckSortField.reloadComponent(0)
    }
    
    @IBAction func sortDirectionChange(_ sender: Any) {
        let settings = UserDefaults.standard
        settings.set(swAscending.isOn, forKey: Constants.kSortDirectionAscending)
        settings.synchronize()
    }
    
    // MARK: UIPickerViewDelegate Methods
    
    // Returns the number of 'columns' to display.
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    // Returns the # of rows in the picker
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return sortOrderItems.count
    }
    
    //Sets the value that is shown for each row in the picker
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int)
        -> String? {
            return sortOrderItems[row]
    }
    
    //If the user chooses from the pickerview, it calls this function;
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        let sortField = sortOrderItems[row]
        let settings = UserDefaults.standard
        settings.set(sortField, forKey: Constants.kSortField)
        settings.synchronize()
    }

}
