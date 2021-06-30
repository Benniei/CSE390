//
//  PokemonTableViewController.swift
//  MyPokemonCards
//
//  Created by Bennie Chen on 6/30/21.
//

import UIKit
import CoreData

class PokemonTableViewController: UITableViewController {

    var pokemon: [NSManagedObject] = []
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButtonItem
    }
    
    override func viewDidAppear(_ animated: Bool) {
        loadFromDatabase()
        tableView.reloadData()
    }

    func loadFromDatabase(){
        let settings = UserDefaults.standard
        
        let sortField = settings.string(forKey: Constants.kSortField)?.lowercased()
        let sortAscending = true
        
        let context = appDelegate.persistentContainer.viewContext
        let request = NSFetchRequest<NSManagedObject>(entityName: "Card")
        
        let sortDesciptor = NSSortDescriptor(key: sortField, ascending: sortAscending)
        let sortDescriptorArray = [sortDesciptor]
        
        request.sortDescriptors = sortDescriptorArray
        
        do{
            pokemon = try context.fetch(request)
        } catch let error as NSError {
            print("Could not fetch \(error)")
        }
        
        while(true){
            var number = -1
            for i in 0...pokemon.count - 1{
                let poke = pokemon[i] as! Card
                if poke.name == "0" || poke.name == "" || poke.name == nil{
                    number = i
                }
            }
            if number == -1 {
                break
            }
            else {
                pokemon.remove(at: number)
                if(pokemon.count == 0){
                    break
                }
            }
        }
    }
    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return pokemon.count
    }

    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "pokeView", for: indexPath)
        let settings = UserDefaults.standard

        // Configure the cell...
        let poke = pokemon[indexPath.row] as! Card
        cell.textLabel?.text = poke.name
        let sortField = settings.string(forKey: Constants.kSortField)?.lowercased()
        
        if(sortField == "type"){
            cell.detailTextLabel?.text = poke.type
        }else{
            let strength = poke.strength
            cell.detailTextLabel?.text =  String(format: "%d", strength as CVarArg)
        }
        return cell
    }
    
    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
        if segue.identifier == "viewCard" {
            let pokeController = segue.destination as? PokemonViewController
            let selectedRow = self.tableView.indexPath(for: sender as! UITableViewCell)?.row
            let selectedTask = pokemon[selectedRow!] as? Card
            pokeController?.currentPokemon = selectedTask
        }
    }
    

}
