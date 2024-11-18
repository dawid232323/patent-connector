import {Routes} from "@angular/router";
import {PatentListingComponent} from "app/features/patent/features/patent-listing/patent-listing.component";
import {PatentListingResolver} from "app/features/patent/features/patent-listing/service/patent-listing.resolver";

export const PATENT_ROUTES: Routes = [
	{
		path: 'patents',
		children: [
			{
				path: 'listing',
				component: PatentListingComponent,
				resolve: {
					businessBranches: PatentListingResolver
				}
			}
		]
	}
];
