/* jshint browser:false, node:true, esnext: true */

var gulp = require('gulp');
var debug = require('gulp-debug'); // jshint ignore:line
var logger = require('gulp-logger');
var filter = require('gulp-filter');
var util = require('gulp-util');
var sourcemaps = require('gulp-sourcemaps');
var concat = require('gulp-concat');
var lazypipe = require('lazypipe');
var uglify = require('gulp-uglify');
var rename = require('gulp-rename');
var replace = require('gulp-replace');
var foreach = require('gulp-foreach');
var amdOptimize = require("amd-optimize");
var argv = require('yargs').argv;

var less = require('gulp-less');
var autoprefix = require('less-plugin-autoprefix');
var minifyCSS = require('gulp-minify-css');
var cache = require('gulp-cached');
var progeny = require('gulp-progeny');
var clone = require('gulp-clone');
var gulpIf = require('gulp-if');
var merge = require('merge2');

var makeSourceMaps = !argv['skip-source-maps'];

var browsers = [
    "explorer >= 8",
    "chrome >= 21",
    "firefox esr",
    "opera >= 15",
    "android >= 2.3",
    "safari >= 6.2.6",
    "explorermobile >= 10",
    "ios >= 6",
    "blackberry >= 10"
].join(",");

var cleanCssOptions = {
    compatibility: 'ie7',
    aggressiveMerging: false,
    advanced: false
};

var fromLess = lazypipe()
    .pipe(logger, { after: 'LESS complete!', extname: '.css', showChange: true })
    .pipe(less, { relativeUrls: true, plugins: [new autoprefix({ browsers: browsers }) ] })
    .pipe(replace, /\.\.\/mobile\//g, ''); // temp hack for the discrepancy between source and generated "source"

var minify = lazypipe()
    .pipe(logger, { after: 'Min CSS complete!', extname: '.min.css', showChange: true } )
    .pipe(minifyCSS, cleanCssOptions)
    .pipe(rename, { suffix: ".min" });

var cacheLessDependencies = lazypipe()
    .pipe(cache, 'less')
    .pipe(progeny, {
        regexp: /^\s*@import\s*(?:\(\w+\)\s*)?['"]([^'"]+)['"]/
    });

var argv = require('yargs').argv;

// uglify
var compress = {
    unsafe       : true,
    hoist_vars   : true,
    warnings     : false,
    pure_getters : true
};

var mangle = {
    except: [ "define" ]
};

function renameModules(match) {
  return match.replace(/['"]([\w\.\-\/]+)?['"]/g, function(_, module) {
    return module == "jquery" ? '"jquery"' : `"${module}.min"`
  });
}

var uglify = lazypipe()
    .pipe(logger, { after: 'uglify complete', extname: '.min.js', showChange: true })
    .pipe(uglify, { compress, mangle, preserveComments: "license" })
    .pipe(replace, /define\(".+?\]/g, renameModules)
    .pipe(rename, { suffix: ".min" });


// AMD gathering

function gatherCustomAmd(stream, file) {
    var moduleId = file.path.match(/kendo\.(.+)\.js/)[1];

    console.log(moduleId);
    return stream.pipe(amdOptimize(`kendo.${moduleId}`, {
        baseUrl: "js",
        exclude: [ 'jquery' ]
    }));
}

var gatherCustom = lazypipe()
    .pipe(foreach, gatherCustomAmd);

gulp.task("custom", function() {
    var files = argv.c;

    if (files.indexOf(",") == -1) {
        throw new util.PluginError({
            task: "custom",
            plugin: "custom",
            message: "please specify more than one component"
        });
    }
    if (!files) {
        throw new util.PluginError({
            task: "custom",
            plugin: "custom",
            message: "please provide a list of the components to be included in the build with -c, separated with ','"
        });
    }

    var included = [];
    return gulp.src(`js/kendo.{${files}}.js`, { base: "js" })
            .pipe(gatherCustom())
            .pipe(filter(function(file) {
                if (included.indexOf(file.path) === -1) {
                    included.push(file.path);
                    return true;
                }  else {
                    return false;
                }
            }))
            .pipe(concat({path: 'js/kendo.custom.js', base: 'js'}))
            .pipe(sourcemaps.init())
            .pipe(uglify())
            .pipe(logger({after: 'source map complete!', extname: '.map', showChange: true}))
            .pipe(sourcemaps.write("./"))
            .pipe(gulp.dest("dist/js"));
});

gulp.task("less",function() {
    var css = gulp.src(`styles/${argv.styles || '**/kendo*.less'}`, { base: "styles" })
        .pipe(fromLess());

    var minCss = css.pipe(clone())
        .pipe(gulpIf(makeSourceMaps, sourcemaps.init()))
        .pipe(minify())
        .pipe(gulpIf(makeSourceMaps, sourcemaps.write("./")));

    return merge(css, minCss)
        .pipe(gulp.dest('dist/styles'));
});
