import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home1',
  templateUrl: './home1.component.html',
  styleUrls: ['./home1.component.css']
})
export class Home1Component implements OnInit {
  constructor() { }

  ngOnInit(): void {
    const formOpenBtn = document.querySelector("#form-open") as HTMLElement;
    const home = document.querySelector(".home") as HTMLElement;
    const formContainer = document.querySelector(".form_container") as HTMLElement;
    const formCloseBtn = document.querySelector(".form_close") as HTMLElement;
    const signupBtn = document.querySelector("#signup") as HTMLElement;
    const loginBtn = document.querySelector("#login") as HTMLElement;
    const pwShowHide = document.querySelectorAll(".pw_hide");

    formOpenBtn.addEventListener("click", () => home.classList.add("show"));
    formCloseBtn.addEventListener("click", () => home.classList.remove("show"));

    pwShowHide.forEach((icon) => {
      icon.addEventListener("click", () => {
        const getPwInput = icon.parentElement?.querySelector<HTMLInputElement>("input");
        if (getPwInput && getPwInput.type === "password") {
          getPwInput.type = "text";
          icon.classList.replace("uil-eye-slash", "uil-eye");
        } else if (getPwInput) {
          getPwInput.type = "password";
          icon.classList.replace("uil-eye", "uil-eye-slash");
        }
      });
    });

    signupBtn.addEventListener("click", (e) => {
      e.preventDefault();
      formContainer.classList.add("active");
    });

    loginBtn.addEventListener("click", (e) => {
      e.preventDefault();
      formContainer.classList.remove("active");
    });
  }
}
