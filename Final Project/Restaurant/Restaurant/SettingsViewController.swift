//
//  SettingsViewController.swift
//  Restaurant
//
//  Created by Bennie Chen on 6/22/21.
//

/*
 * Bennie Chen
 * ID: 112737201
 * CSE 390: MObile App Development
 * Final Project
 * Icon links
 *      https://thenounproject.com/term/minimal/
 *      https://www.surrenderat20.net/2019/07/731-pbe-update-new-summoner-icons-tft.html
 */

import UIKit

class SettingsViewController: UIViewController, UIPickerViewDelegate, UIPickerViewDataSource {

    @IBOutlet weak var pckSortField: UIPickerView!
    @IBOutlet weak var swAscending: UISwitch!
    
    let sortOrderItems: Array<String> = ["Date", "Name", "Number of People", "Price"]
    
    /**
     Method called when the screnen is loaded
     */
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        pckSortField.dataSource = self
        pckSortField.delegate = self
    }
    
    /**
     Method called when the view appears
     */
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
    
    /**
     This nethod  tracks when the Ascening Sort status changes
     - Parameters:
        - sender:Any
     */
    @IBAction func sortDrectionChanged(_ sender: Any) {
        let settings = UserDefaults.standard
        settings.set(swAscending.isOn, forKey: Constants.kSortDirectionAscending)
        settings.synchronize()
    }
    
    // MARK: UIPickerViewDelegate Methods
    
    /**
    Number of Columns
     - Parameters:
        - pickerView:view holding the Picker
     - Returns: numberof 'columns' to display
     */
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    /**
     Number of Rows
     - Parameters:
        - pickerView: view holding the Picker
     
    - Returns: Number of rows in the picker
     */
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return sortOrderItems.count
    }
    
    //Sets the value that is shown for each row in the picker
    /**
    Sets Value of Each row
     - Parameters:
        - pickerView: view holidng the Piker
     - Returns: value that is shown for each row in the picker
     */
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int)
        -> String? {
            return sortOrderItems[row]
    }
    
    //If the user chooses from the pickerview, it calls this function;
    /**
     This function is called when the user chooses somethin in the pickerView
     - Parameters:
        - pickerView: view holding the picker
        - didSelectRow: int value of what row is selected
     */
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        let sortField = sortOrderItems[row]
        let settings = UserDefaults.standard
        settings.set(sortField, forKey: Constants.kSortField)
        settings.synchronize()
    }

}
