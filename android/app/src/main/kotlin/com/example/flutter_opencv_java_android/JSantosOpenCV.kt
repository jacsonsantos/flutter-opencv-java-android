package com.example.flutter_opencv_java_android

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar

import org.opencv.android.OpenCVLoader
import org.opencv.imgproc.Imgproc;

class JSantosOpenCV {
    var OpenCVFLag = false

    init(@NonNull call: MethodCall, @NonNull result: Result) {
        if (!OpenCVFLag) {
            if (!OpenCVLoader.initDebug()) {
                println("Couldn't initialize OpenCV")
            } else {
                OpenCVFLag = true;
            }
        }

        when (call.method) {
            "canny" -> {
                try {
                    // @TODO: Aqui vai minha regra de negocio
                    // public static void Canny(Mat image, Mat edges, double threshold1, double threshold2, int apertureSize, boolean L2gradient)
                    var byteArray = ByteArray(0)

                    var data = call.argument<Mat>("image") as Mat
                    val src = Imgcodecs.imdecode(MatOfByte(*data), Imgcodecs.IMREAD_UNCHANGED)
                    
                    var dis = call.argument<Mat>("edges") as Mat
                    val dst = Imgcodecs.imdecode(MatOfByte(*dis), Imgcodecs.IMREAD_UNCHANGED)
                    
                    Imgproc.Canny(
                        src,
                        dst,
                        call.argument<Double>("threshold1") as Double,
                        call.argument<Double>("threshold2") as Double,
                        call.argument<Int>("apertureSize") as Int,
                        call.argument<Boolean>("L2gradient") as Boolean
                    )

                    val matOfByte = MatOfByte()
                    Imgcodecs.imencode(".jpg", dst, matOfByte)
                    byteArray = matOfByte.toArray()

                    result.success(byteArray)
                } catch (e: Exception) {
                result.error("OpenCV Error", "Android: "+e.message, e)
                }
            }
        }
    }
}