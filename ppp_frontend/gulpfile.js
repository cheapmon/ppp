var gulp = require('gulp');
var server = require('gulp-server-livereload');

gulp.task('webserver', function () {
    gulp.src('app')
        .pipe(server({port: 8003, host: 'localhost'}));
});