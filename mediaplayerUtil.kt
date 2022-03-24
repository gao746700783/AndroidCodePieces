
val mPlayer = MediaPlayer()
//mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
mPlayer.setOnErrorListener(object : MediaPlayer.OnErrorListener {
    override fun onError(p0: MediaPlayer?, p1: Int, p2: Int): Boolean {
        Timber.e("what:$p1 extra:$p2")
        return true
    }

})
mPlayer.setOnBufferingUpdateListener(object : MediaPlayer.OnBufferingUpdateListener {
    override fun onBufferingUpdate(p0: MediaPlayer?, p1: Int) {
        Timber.e("what:$p1")
    }
})
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
    val attr = AudioAttributes.Builder()
        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
        .setUsage(AudioAttributes.USAGE_MEDIA)
        .build()
    mPlayer.setAudioAttributes(attr)
}
mPlayer.setOnPreparedListener { it.start() }
mPlayer.reset()
val url = "https://alioss.jwmapp.com/chat/single/173827332/e95ee834cb17408b833447aae076f20d.amr"
//mPlayer.setDataSource(this, Uri.parse(url))
mPlayer.setDataSource(url)
mPlayer.prepareAsync()
