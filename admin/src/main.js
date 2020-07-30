import Vue from 'vue'
import App from './app.vue'
import router from './router'
import axios from 'axios'
import filter from './filters/filter'

Vue.config.productionTip = false;
Vue.prototype.$ajax = axios;

/**
 * axios拦截器
 */
axios.interceptors.request.use(function (config) {
  console.log("Request: ", config);
  return config;
}, error => {});
axios.interceptors.response.use(function (response) {
  console.log("Response: ", response);
  return response;
}, error => {});

// global filter
Object.keys(filter).forEach(key => {
  Vue.filter(key, filter[key])
});

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
