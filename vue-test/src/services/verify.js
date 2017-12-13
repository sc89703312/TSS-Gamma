import Axios from 'axios'

export default {
  login (params) {
    return Axios.post('/login', params)
  },
  register (params) {
    return Axios.post('/register', params)
  }
}
