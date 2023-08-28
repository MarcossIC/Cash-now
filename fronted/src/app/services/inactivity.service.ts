import { HostListener, Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { TokenService } from './token.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class InactivityService {

  private inactivityTimer: any;

  constructor(private authService: AuthService, private tokenService:TokenService,
    private router: Router) { }

  startInactivityTimer(): void{
    this.authService.userActivity.subscribe(() => {
      clearTimeout(this.inactivityTimer);
      this.inactivityTimer = setTimeout(() => {
        this.tokenService.logOut();
        this.router.navigateByUrl('auth/login');

      }, 30000); // 5 minutos de inactividad antes de cerrar sesión
    });
  }

  @HostListener('window:mousemove', ['$event'])
  @HostListener('window:keypress', ['$event'])
  onUserActivity(event: any): void {
    this.authService.userActivity.next(true);
  }

}
