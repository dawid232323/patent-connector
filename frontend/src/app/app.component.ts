import {Component} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {NavBarComponent} from "src/app/shared/features/nav-bar/nav-bar.component";
import {CommonFeaturesModule} from "app/features/common/common-features.module";

@Component({
	selector: 'app-root',
	standalone: true,
	imports: [RouterOutlet, NavBarComponent, CommonFeaturesModule],
	templateUrl: './app.component.html',
	styleUrl: './app.component.scss'
})
export class AppComponent {
	title = 'Patent Connector';
}
