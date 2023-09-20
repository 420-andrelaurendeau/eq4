import http from "../constants/http";

class Service {
    getAllUsers() {
        return http.get('/users')
    }

    getUser(id) {
        return http.get('/users/${id}');
    }
}

export default new Service();