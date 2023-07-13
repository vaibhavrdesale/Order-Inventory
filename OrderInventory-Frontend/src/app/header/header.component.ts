import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {

  constructor(public authService: AuthService, private router: Router) {}


  logout(): void {
    this.authService.logout().subscribe(
      () => {
        // Redirect to the login page after successful logout
        this.router.navigate(['/login']);
      },
      (error: any) => {
        // Handle logout error
      }
    );
  }


}
