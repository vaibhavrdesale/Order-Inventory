import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  constructor() { }

  ngOnInit(): void {
    const formOpenBtn = document.querySelector("#form-open") as HTMLElement;
    const home = document.querySelector(".home") as HTMLElement;
    const formCloseBtn = document.querySelector(".form_close") as HTMLElement;
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


  }
}
