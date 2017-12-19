import 'rxjs/add/operator/switchMap';
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Params} from '@angular/router';
import {Location} from '@angular/common';

import {Uporabnik} from './models/uporabnik';
import {UporabnikService} from './services/uporabnik.service';
import {ZbraneTocke} from "../ZbraneTocke/models/ZbraneTocke";

/*
import {ZbraneTocke} from '../ZbraneTocke/models/ZbraneTocke';

import {ZbraneTockeService} from '../ZbraneTocke/services/zbraneTocke.service';
*/
@Component({
    moduleId: module.id,
    selector: 'uporabnik-podrobnosti',
    templateUrl: 'uporabnik-podrobnosti.component.html'
})
export class UporabnikPodrobnostiComponent implements OnInit {
    uporabnik: Uporabnik;
    zbraneTocke: ZbraneTocke;

    constructor(private uporabnikService: UporabnikService,
                private route: ActivatedRoute,
                private location: Location
    ) {
    }

    ngOnInit(): void {
        this.route.params
            .switchMap((params: Params) => this.uporabnikService.getUporabnik(params['uporabnisko_ime']))
            .subscribe(response => {
                this.uporabnik = response[0];
                this.zbraneTocke = response[1];
            });
    }

    nazaj(): void {
        this.location.back();
    }
}
