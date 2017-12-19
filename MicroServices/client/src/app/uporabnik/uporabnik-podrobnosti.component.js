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
require("rxjs/add/operator/switchMap");
var core_1 = require("@angular/core");
var router_1 = require("@angular/router");
var common_1 = require("@angular/common");
var uporabnik_service_1 = require("./services/uporabnik.service");
/*
import {ZbraneTocke} from '../ZbraneTocke/models/ZbraneTocke';

import {ZbraneTockeService} from '../ZbraneTocke/services/zbraneTocke.service';
*/
var UporabnikPodrobnostiComponent = (function () {
    function UporabnikPodrobnostiComponent(uporabnikService, route, location) {
        this.uporabnikService = uporabnikService;
        this.route = route;
        this.location = location;
    }
    UporabnikPodrobnostiComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params
            .switchMap(function (params) { return _this.uporabnikService.getUporabnik(params['uporabnisko_ime']); })
            .subscribe(function (response) {
            _this.uporabnik = response[0];
            _this.zbraneTocke = response[1];
        });
    };
    UporabnikPodrobnostiComponent.prototype.nazaj = function () {
        this.location.back();
    };
    UporabnikPodrobnostiComponent = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'uporabnik-podrobnosti',
            templateUrl: 'uporabnik-podrobnosti.component.html'
        }),
        __metadata("design:paramtypes", [uporabnik_service_1.UporabnikService,
            router_1.ActivatedRoute,
            common_1.Location])
    ], UporabnikPodrobnostiComponent);
    return UporabnikPodrobnostiComponent;
}());
exports.UporabnikPodrobnostiComponent = UporabnikPodrobnostiComponent;
//# sourceMappingURL=uporabnik-podrobnosti.component.js.map