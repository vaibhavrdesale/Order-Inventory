// register.component.ts
import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  email!: string;
  password!: string;

  constructor(private authService: AuthService, private router: Router) {}

  register(): void {
    this.authService.register(this.email, this.password).subscribe(
      (response: any) => {
        // Handle successful registration
        this.router.navigate(['/login']);
        alert(response)
      },
      (error: any) => {
        alert(JSON.stringify(error.error.error));
      }
    );
  }
}
