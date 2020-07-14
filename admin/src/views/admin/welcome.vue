<template>
  <div>
    <h1>Hello, Admin!</h1>
  </div>
</template>

<script>
    export default {
        name: 'welcome',
        mounted: function() {
            this.$parent.activateSidebar("dashboard-sidebar")
        },
        methods: {
        }
    }
</script>