import http from "../constants/http";


class DataService{

    getAllOffers(){
        return http.get("/offers");
    }
    getOffersByEmployerId(id){
        return http.get(`/employer/offers/${id}`);
    }
}

export default new DataService();