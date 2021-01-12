import { Component, OnDestroy, Inject, OnInit, ViewChild } from '@angular/core';
import { DOCUMENT } from '@angular/common';
import { navItems } from './_nav';
import { JwtService } from '../../services/jwt.service';
import { User } from '../../models/user';
import { DefaultLayoutService } from './default-layout.service';
import { Router } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import * as $ from 'jquery';


@Component({
  selector: 'app-dashboard',
  templateUrl: './default-layout.component.html'
})
export class DefaultLayoutComponent implements OnDestroy, OnInit {
  public navData: Array<any> = new Array<any>();
  public _navItems = navItems;
  public sidebarMinimized = true;
  private changes: MutationObserver;
  public element: HTMLElement;
  public navDataisload: boolean = false;
  public user: User;
  public isBoss: boolean = false;
  public isCustomer: boolean = false;
  public error: string = '';
  public modifySecretForm: FormGroup;

  home: string;
  status: { isOpen: boolean } = { isOpen: false };
  disabled: boolean = false;
  isDropup: boolean = true;
  autoClose: boolean = false;
  isConfirm: boolean = false;
  @ViewChild('modifySecretModal', { static: false }) public modifySecretModal: ModalDirective;

  constructor(@Inject(DOCUMENT) _document?: any, private defaultLayoutService?: DefaultLayoutService,
    private fb?: FormBuilder,
    private jwtService?: JwtService, private router?: Router) {
    this.changes = new MutationObserver((mutations) => {
      this.sidebarMinimized = _document.body.classList.contains('sidebar-minimized');
    });
    this.element = _document.body;
    this.changes.observe(<Element>this.element, {
      attributes: true,
      attributeFilter: ['class']
    });

  }

  ngOnInit() {
    // Called after the constructor, initializing input properties, and the first call to ngOnChanges.
    // Add 'implements OnInit' to the class.
    this.user = new User(this.jwtService);
    if (this.user.isBoss) {
      this.home = '/boss';
      this.isBoss = true;
    } else if (this.user.isCustomer) {
      this.home = '/customer';
      this.isCustomer = true;
    }
    /* Modify Secret Area*/
    this.modifySecretForm = this.fb.group({
      username: [this.user.getUsername(), Validators.required],
      currentPassword: ['', [Validators.pattern('^[a-zA-Z0-9-_]{8,20}')]],
      newPassword: ['', [Validators.pattern('^[a-zA-Z0-9-_]{8,20}')]],
      confirmPassword: ['', [Validators.pattern('^[a-zA-Z0-9-_]{8,20}')]],
      rememberMe: [true]
    });
    this.modifySecretAreaOnchange();
  }


  modifySecretAreaOnchange() {
    const currentPassword = 'currentPassword';
    const newPassword = 'newPassword';
    const confirmPassword = 'confirmPassword';
    this.modifySecretForm.get(currentPassword).valueChanges.subscribe(
      () => this.modifySecretForm.get(currentPassword).valid ?
        this.showIsValidById(currentPassword) : this.hideIsInvalidById(currentPassword)
    );
    this.modifySecretForm.get(newPassword).valueChanges.subscribe(
      (value) => {
        this.modifySecretForm.get(newPassword).valid ?
          this.showIsValidById(newPassword) : this.hideIsInvalidById(newPassword);
        if (value !== this.modifySecretForm.get(confirmPassword).value) {
          this.hideIsInvalidById(confirmPassword);
        } else {
          this.isConfirm = true;
        }
      }
    );
    this.modifySecretForm.get(confirmPassword).valueChanges.subscribe(
      value => {
        if (value === this.modifySecretForm.get(newPassword).value) {
          this.showIsValidById(confirmPassword);
          this.isConfirm = true;
        } else {
          this.hideIsInvalidById(confirmPassword);
          this.isConfirm = false;
        }
      }
    );
  }

  showIsValidById(id: string) {
    $('#' + id).addClass('is-valid');
    $('#' + id).removeClass('is-invalid');
  }

  hideIsInvalidById(id: string) {
    $('#' + id).removeClass('is-valid');
    $('#' + id).addClass('is-invalid');
  }

  changeToDashboard() {
    this.router.navigate([this.home]);
  }

  logout() {
    this.jwtService.removeToken();
  }

  modifySecret() {
    if (this.modifySecretForm.dirty && this.modifySecretForm.valid && this.isConfirm) {
      this.defaultLayoutService.modifySecret(this.modifySecretForm).subscribe(
        response => {
          this.logout();
          this.router.navigate(['login']);
        },
        error => {
          this.error = error.error.text;
        }
      );
    }
  }

  toggleDropdown($event: MouseEvent): void {
    $event.preventDefault();
    $event.stopPropagation();
    this.status.isOpen = !this.status.isOpen;
  }

  change(value: boolean): void {
    this.status.isOpen = value;
  }


  ngOnDestroy(): void {
    this.changes.disconnect();
    this.status.isOpen = false;
  }
}
