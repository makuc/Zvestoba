"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var http_1 = require("@angular/common/http");
var forkJoin_1 = require("rxjs/observable/forkJoin");
var UporabnikService = (function () {
    function UporabnikService(http) {
        this.http = http;
        this.headers = new http_1.HttpHeaders({ 'Content-Type': 'application/json' });
        this.url = 'http://localhost:8080/v1/uporabniki';
        this.urlTock = 'http://localhost:8080/v1/tocke';
    }
    UporabnikService.prototype.getUporabniki = function () {
        return this.http.get(this.url)
            .toPromise()
            .then(function (response) { return response; })
            .catch(this.handleError);
    };
    UporabnikService.prototype.getUporabnik = function (uporabnisko_ime) {
        var url = this.url + "/" + uporabnisko_ime;
        var urlTock = this.urlTock + "/" + uporabnisko_ime;
        return forkJoin_1.forkJoin([this.http.get(url), this.http.get(urlTock)]).toPromise().then(function (response) { return response; });
    };
    UporabnikService.prototype.delete = function (uporabnisko_ime) {
        var url = this.url + "/" + uporabnisko_ime;
        return this.http
            .delete(url, { headers: this.headers })
            .toPromise()
            .then(function () { return null; }, function () { return null; })
            .catch(this.handleError);
    };
    UporabnikService.prototype.create = function (uporabnik) {
        return this.http
            .post(this.url, JSON.stringify(uporabnik), { headers: this.headers })
            .toPromise()
            .then()
            .catch(this.handleError);
    };
    UporabnikService.prototype.handleError = function (error) {
        console.error('Prišlo je do napake', error);
        return Promise.reject(error.message || error);
    };
    UporabnikService = __decorate([
        core_1.Injectable(),
        __metadata("design:paramtypes", [http_1.HttpClient])
    ], UporabnikService);
    return UporabnikService;
}());
exports.UporabnikService = UporabnikService;
//# sourceMappingURL=uporabnik.service.js.map