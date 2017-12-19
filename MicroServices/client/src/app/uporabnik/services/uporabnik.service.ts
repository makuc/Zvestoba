import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {Uporabnik} from '../models/uporabnik';
import {ZbraneTocke} from '../../ZbraneTocke/models/ZbraneTocke';
import {forkJoin} from 'rxjs/observable/forkJoin';

@Injectable()
export class UporabnikService {

    private headers = new HttpHeaders({'Content-Type': 'application/json'});
    private url = 'http://localhost:8080/v1/uporabniki';
    private urlTock = 'http://localhost:8080/v1/tocke';

    constructor(private http: HttpClient) {
    }

    getUporabniki(): Promise<Uporabnik[]> {
        return this.http.get(this.url)
            .toPromise()
            .then(response => response as Uporabnik[])
            .catch(this.handleError);
    }

    getUporabnik(uporabnisko_ime: string): Promise<[Uporabnik, ZbraneTocke]> {
        const url = `${this.url}/${uporabnisko_ime}`;
        const urlTock = `${this.urlTock}/${uporabnisko_ime}`;

        return forkJoin([this.http.get(url), this.http.get(urlTock)]).toPromise().then(response => response as [Uporabnik, ZbraneTocke]);
    }

    delete(uporabnisko_ime: string): Promise<void> {
        const url = `${this.url}/${uporabnisko_ime}`;
        return this.http
            .delete(url, {headers: this.headers})
            .toPromise()
            .then(() => null, () => null)
            .catch(this.handleError);
    }

    create(uporabnik: Uporabnik): Promise<void> {
        return this.http
            .post(this.url, JSON.stringify(uporabnik), {headers: this.headers})
            .toPromise()
            .then()
            .catch(this.handleError);
    }

    private handleError(error: any): Promise<any> {
        console.error('Prišlo je do napake', error);
        return Promise.reject(error.message || error);
    }
}

