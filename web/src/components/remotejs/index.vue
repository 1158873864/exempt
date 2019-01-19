<script>
export default {
    'remotejs': {
      render(createElement) {
        return createElement('script', { attrs: { type: 'text/javascript', src: this.src ,async:'async', defer:'defer'} });
      },
      props: {
        src: { type: String, required: true },
      },
    },
  }

</script>
