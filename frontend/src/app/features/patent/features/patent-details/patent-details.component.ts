import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Patent} from "app/shared/types/patent.types";
import {CommentType} from "app/shared/types/comment.types";

@Component({
  selector: 'app-patent-details',
  templateUrl: './patent-details.component.html',
  styleUrl: './patent-details.component.scss'
})
export class PatentDetailsComponent implements OnInit {

	patent!: Patent;
	patentId!: number;

	readonly commentType = CommentType;

	constructor(private activatedRoute: ActivatedRoute) {
	}

	ngOnInit() {
		this.initPatentData();
	}

	private initPatentData() {
		this.patent = this.activatedRoute.snapshot.data['patent'];
		this.patentId = this.activatedRoute.snapshot.params['id'];
	}

}
