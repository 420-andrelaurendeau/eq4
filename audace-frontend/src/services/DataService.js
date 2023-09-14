import http from "../constants/http";

class DataService{

    getAllOffers(){
        return http.get("");
    }
    getOffersByEmployerId(id){
        return http.get("");
    }
}

export default new DataService();