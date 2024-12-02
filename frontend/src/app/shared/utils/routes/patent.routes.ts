import {Routes} from "@angular/router";
import {PatentListingComponent} from "app/features/patent/features/patent-listing/patent-listing.component";
import {PatentListingResolver} from "app/features/patent/features/patent-listing/service/patent-listing.resolver";
import {PatentDetailsComponent} from "app/features/patent/features/patent-details/patent-details.component";
import {PatentDetailsResolver} from "app/features/patent/features/patent-details/resolver/patent-details.resolver";

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
			},
			{
				path: 'details/:id',
				component: PatentDetailsComponent,
				resolve: {
					patent: PatentDetailsResolver
				}
			}
		]
	}
];
