import { Component } from '@angular/core';
import { CurrentAccountService } from '../current-account.service';
import { NeedsService } from '../needs.service';
import { Need } from '../need';
import { NEEDS } from '../mock-needs'; 
import { Subject } from 'rxjs';
import { debounceTime } from 'rxjs/operators';
import { ACCOUNTS } from '../mock-accounts';
import { STATISTICS } from '../mock-statistics';
import { Statistics } from '../statistics';
@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css'],
})
export class AdminDashboardComponent {
  statistics = STATISTICS;

  needs: Need[] = NEEDS; // Initialize needs from mock data
  admin: boolean = true;
  selectedNeed?: Need;
  private nextId = 1;
  isLoggedIn: boolean = false;
  
  numUsers: number = ACCOUNTS.length;
  totalAmountOfNeeds: number = NEEDS.length;
  totalAmountOfNeedsPurchased : number= this.statistics.getTotalItemsSold(); 
  amountMoneyRaised: number= this.statistics.getTotalRevenue(); 
  averageCostOfNeeds: number = this.calculateAverageCost();



  constructor(private needsService: NeedsService, private currentAccountService: CurrentAccountService) {
    this.admin = currentAccountService.getAccount()?.admin ?? true; 
    this.isLoggedIn = currentAccountService.isLoggedIn();

  }

  
  private calculateAverageCost(): number {
    if (NEEDS.length === 0) return 0;
    const totalCost = NEEDS.reduce((sum, need) => sum + need.cost, 0);
    return totalCost / NEEDS.length;
  }

  addNeed(name: string, cost: number, quantity: number, type: string, isFulfilled: boolean) {
    if (name && cost && quantity && type) {  // Ensure all fields are filled
      const newNeed: Need = {
        id: this.nextId++, // Increment the ID
        name: name,
        cost: +cost,  // Convert to number
        quantity: +quantity,  // Convert to number
        type: type,
        isFulfilled: isFulfilled
      };

      // this.needs.push(newNeed); // Add new need to local array
      this.needsService.addNeed(newNeed);
      console.log('Added new need:', newNeed);
    } else {
      console.log('All fields must be filled');
    }
  }

  delete(need: Need): void {
  
    this.needsService.deleteNeed(need.id);
  }

  updateNeed(): void {
    if (this.selectedNeed) { 
    
      this.needsService.updateNeed(this.selectedNeed)
    } else {
      console.log('No need selected for update');
    }
  }

  onSelect(need: Need): void {
    this.selectedNeed = need;
    console.log('Selected need:', this.selectedNeed);
  }
  

  

}
