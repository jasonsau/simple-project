import { MediaMatcher } from '@angular/cdk/layout';
import { isPlatformBrowser } from '@angular/common';
import { Component, inject, PLATFORM_ID, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterOutlet, RouterLink } from '@angular/router';

@Component({
  selector: 'app-navbar',
	imports: [RouterOutlet, MatToolbarModule, MatButtonModule, MatIconModule, MatSidenavModule, MatListModule, RouterLink],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
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
