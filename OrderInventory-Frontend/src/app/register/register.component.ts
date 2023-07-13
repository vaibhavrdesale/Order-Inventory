// register.component.ts
import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  email!: string;
  password!: string;

  constructor(private authService: AuthService) {}

  register(): void {
    this.authService.register(this.email, this.password).subscribe(
      (response: any) => {
        // Handle successful registration
      },
      (error: any) => {
        // Handle registration error
      }
    );
  }
}
