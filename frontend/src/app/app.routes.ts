import { Routes } from '@angular/router';
import {COMMON_ROUTES} from "app/shared/utils/routes/common.routes";
import {PATENT_ROUTES} from "app/shared/utils/routes/patent.routes";
import {EVENT_ROUTES} from "app/shared/utils/routes/event.routes";
import {INVENTION_DEMAND_ROUTES} from "app/shared/utils/routes/invention-demand.routes";

export const routes: Routes = [
	...COMMON_ROUTES,
	...PATENT_ROUTES,
	...EVENT_ROUTES,
	...INVENTION_DEMAND_ROUTES,
	{
		path: '**',
		redirectTo: 'patents/listing'
	}
];
