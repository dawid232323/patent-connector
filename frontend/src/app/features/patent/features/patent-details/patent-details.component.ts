import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Patent} from "app/shared/types/patent.types";

@Component({
  selector: 'app-patent-details',
  templateUrl: './patent-details.component.html',
  styleUrl: './patent-details.component.scss'
})
export class PatentDetailsComponent implements OnInit {

	patent!: Patent;

	constructor(private activatedRoute: ActivatedRoute) {
	}

	ngOnInit() {
		this.initPatentData();
		console.log(this.patent);
	}

	private initPatentData() {
		this.patent = this.activatedRoute.snapshot.data['patent'];
	}

}
