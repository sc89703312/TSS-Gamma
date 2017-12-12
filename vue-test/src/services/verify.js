import Axios from 'axios'

export default {
  login (params) {
    return Axios.post('/login', params)
  },
  register ({type, username, email, password}) {
    return {
      data: {}
    }
  }
}
