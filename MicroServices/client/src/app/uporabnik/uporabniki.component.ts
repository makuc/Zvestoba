import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

import {Uporabnik} from './models/uporabnik';
import {UporabnikService} from './services/uporabnik.service';

@Component({
    moduleId: module.id,
    selector: 'vsi-uporabniki',
    templateUrl: 'uporabniki.component.html'
})
export class UporabnikiComponent implements OnInit {
    uporabniki: Uporabnik[];
    uporabnik: Uporabnik;

    constructor(private uporabnikService: UporabnikService,
                private router: Router) {
    }

    getUporabniki(): void {
        this.uporabnikService
            .getUporabniki()
            .then(uporabniki => this.uporabniki = uporabniki);
    }

    ngOnInit(): void {
        this.getUporabniki();
    }

    naPodrobnosti(uporabnik: Uporabnik): void {
        this.uporabnik = uporabnik;
        this.router.navigate(['/uporabniki', this.uporabnik.uporabnisko_ime]);
    }

    delete(uporabnik: Uporabnik): void {
        this.uporabnikService
            .delete(uporabnik.uporabnisko_ime)
            .then(() => {
                this.uporabniki = this.uporabniki.filter(u => u !== uporabnik);
                if (this.uporabnik === uporabnik) {
                    this.uporabnik = null;
                }
            });
    }

    dodajUporabnika(): void {
        this.router.navigate(['/dodajuporabnika']);
    }

}
