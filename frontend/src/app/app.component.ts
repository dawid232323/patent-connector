import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {MatButton} from "@angular/material/button";
import {MatToolbar} from "@angular/material/toolbar";
import {MatIcon} from "@angular/material/icon";
import {MatDivider} from "@angular/material/divider";
import {MatProgressSpinner} from "@angular/material/progress-spinner";
import {MatRadioButton, MatRadioGroup} from "@angular/material/radio";
import {MatProgressBar} from "@angular/material/progress-bar";
import {NavBarComponent} from "src/app/shared/features/nav-bar/nav-bar.component";

@Component({
  selector: 'app-root',
  standalone: true,
	imports: [RouterOutlet, MatButton, MatToolbar, MatIcon, MatDivider, MatProgressSpinner, MatRadioGroup, MatRadioButton, MatProgressBar, NavBarComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'Patent Connector';
}
