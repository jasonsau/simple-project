import { Component, inject, OnDestroy, PLATFORM_ID, signal } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { MatListModule } from '@angular/material/list';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MediaMatcher} from '@angular/cdk/layout';
import { isPlatformBrowser } from '@angular/common';

@Component({
	selector: 'app-root',
	imports: [RouterOutlet, MatToolbarModule, MatButtonModule, MatIconModule, MatSidenavModule, MatListModule, RouterLink],
	templateUrl: './app.component.html',
	styleUrl: './app.component.css'
})
export class AppComponent implements OnDestroy{
	protected readonly isMobile = signal(true);
	private readonly _mobileQuery: MediaQueryList;
	private readonly _mobileQueryListener: () => void;
	isBrowser: boolean;
	platform = inject(PLATFORM_ID);
	title = 'SimpleProject';


	constructor() {
		const media = inject(MediaMatcher)
		this.isBrowser = isPlatformBrowser(this.platform);
		this._mobileQuery = media.matchMedia('(max-width: 600px)');
		this.isMobile.set(this._mobileQuery.matches);
		this._mobileQueryListener = () => this.isMobile.set(this._mobileQuery.matches);
	}
	ngOnDestroy(): void {
		if (this.isBrowser){
			this._mobileQuery.removeEventListener('change', this._mobileQueryListener);
			console.log("Se esta ejecutando el onDestroy")
		}
	}
}
