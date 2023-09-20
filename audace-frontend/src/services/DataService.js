import http from "../constants/http";


class DataService{

    getAllOffers(){
        return http.get("/offers");
    }
    getAllOfferByEmployerId(id){
        return http.get(`/employers/${id}/offers`);
    }
}

export default new DataService();