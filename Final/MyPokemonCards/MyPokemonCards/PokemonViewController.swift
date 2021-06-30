//
//  PokemonViewController.swift
//  MyPokemonCards
//
//  Created by Bennie Chen on 6/30/21.
//

import UIKit
import CoreData

class PokemonViewController: UIViewController {

    @IBOutlet weak var nameView: UITextField!
    @IBOutlet weak var typeView: UITextField!
    @IBOutlet weak var npnView: UITextField!
    @IBOutlet weak var generationView: UITextField!
    @IBOutlet weak var strengthView: UITextField!
    @IBOutlet weak var clearButtton: UIButton!
    
    var currentPokemon: Card?
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    
    override func viewDidAppear(_ animated: Bool) {

        if currentPokemon != nil {
            if currentPokemon?.name != nil {
                nameView.text = currentPokemon?.name
                typeView.text = currentPokemon?.type
                let npn = currentPokemon!.npn
                npnView.text = String(format: "%d", npn as CVarArg)
                generationView.text = currentPokemon?.generation
                let strength = currentPokemon!.strength
                strengthView.text = String(format: "%d", strength as CVarArg)
                return
            }

        }
        else{
            let context = appDelegate.persistentContainer.viewContext
            currentPokemon = Card(context: context)
            navigationItem.rightBarButtonItem = UIBarButtonItem(barButtonSystemItem: .save, target: self, action: #selector(self.savePokemon))
        }
        
    }
    
   @objc func savePokemon(_ sender: Any) {
        if nameView.text == "" || typeView.text == ""{
            return
        }
        currentPokemon?.name = nameView.text
        currentPokemon?.type = typeView.text
        let npnString = npnView.text
        let npn = Int32(npnString!)
        if npn == 0{
            return
        }
        currentPokemon?.npn = npn!
        currentPokemon?.generation = generationView.text
        let strString = strengthView.text
        let str = Int32(strString!)
        currentPokemon?.strength = str!
        appDelegate.saveContext()
    }
    
    @IBAction func clearButton(_ sender: Any) {
        currentPokemon = nil
        nameView.text = ""
        typeView.text = ""
        npnView.text = ""
        generationView.text = ""
        strengthView.text = ""
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
