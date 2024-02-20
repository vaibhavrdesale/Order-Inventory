// login.component.ts
import { Component } from '@angular/core';
import { AuthResponse } from '../Interface/auth-response.model';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email!: string;
  password!: string;

  constructor(private authService: AuthService, private router: Router) {
    if (localStorage.getItem('token')) {
      this.router.navigate(['/home1']);
    }
    
  }

  login(): void {
    this.authService.login(this.email, this.password).subscribe(
      (response: AuthResponse) => {
        // Save token to local storage
        localStorage.setItem('token', response.token);
        
        // Redirect to protected route 
        this.router.navigate(['/home1']);
      },
      (error: any) => {
        alert("username or password is wrong")
      }
    );
  }
  
}
