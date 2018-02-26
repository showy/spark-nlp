from pyspark import SparkContext
from pyspark.ml.wrapper import JavaWrapper


class ExtendedJavaWrapper(JavaWrapper):
    def __init__(self, java_obj):
        super(ExtendedJavaWrapper, self).__init__(java_obj)
        self.sc = SparkContext._active_spark_context
        self.java_obj = self._java_obj

    def apply(self):
        return self._java_obj

    def new_java_obj(self, java_class, *args):
        return self._new_java_obj(java_class, *args)

    def new_java_array(self, pylist, java_class):
        """
        ToDo: Inspired from spark 2.2.0. Delete if we upgrade
        """
        java_array = self.sc._gateway.new_array(java_class, len(pylist))
        for i in range(len(pylist)):
            java_array[i] = pylist[i]
        return java_array


class _RegexRule(ExtendedJavaWrapper):
    def __init__(self, rule, identifier):
        super(_RegexRule, self).__init__("com.johnsnowlabs.nlp.util.regex.RegexRule")
        self._java_obj = self._new_java_obj(self._java_obj, rule, identifier)


class _ExternalResource(ExtendedJavaWrapper):
    def __init__(self, path, read_as, options):
        super(_ExternalResource, self).__init__("com.johnsnowlabs.nlp.util.io.ExternalResource.fromJava")
        self._java_obj = self._new_java_obj(self._java_obj, path, read_as, options)


class _ConfigLoaderGetter(ExtendedJavaWrapper):
    def __init__(self):
        super(_ConfigLoaderGetter, self).__init__("com.johnsnowlabs.util.ConfigLoader.getConfigPath")
        self._java_obj = self._new_java_obj(self._java_obj)


class _ConfigLoaderSetter(ExtendedJavaWrapper):
    def __init__(self, path):
        super(_ConfigLoaderSetter, self).__init__("com.johnsnowlabs.util.ConfigLoader.setConfigPath")
        self._java_obj = self._new_java_obj(self._java_obj, path)

class _DownloadModel(ExtendedJavaWrapper):
    def __init__(self, reader, name, language):
        super(_DownloadModel, self).__init__("com.johnsnowlabs.pretrained.PythonResourceDownloader.downloadModel")
        self._java_obj = self._new_java_obj(self._java_obj, reader, name, language)

class _DownloadPipeline(ExtendedJavaWrapper):
    def __init__(self, name, language):
        super(_DownloadPipeline, self).__init__("com.johnsnowlabs.pretrained.PythonResourceDownloader.downloadPipeline")
        self._java_obj = self._new_java_obj(self._java_obj, name, language)

# predefined pipelines
class _DownloadPredefinedPipeline(ExtendedJavaWrapper):
    def __init__(self, java_path):
        super(_DownloadPredefinedPipeline, self).__init__(java_path)
        self._java_obj = self._new_java_obj(self._java_obj)

