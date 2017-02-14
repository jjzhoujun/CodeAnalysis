package cn.mycommons.codeanalysis

import org.gradle.api.DefaultTask
import org.gradle.api.plugins.quality.FindBugs
import org.gradle.api.tasks.TaskAction

/**
 * MyCheckstyleTask <br/>
 * Created by xiaqiulei on 2017-02-14.
 */
public class AnalyseFindbugsTask extends DefaultTask {

    @TaskAction
    void doAction() {
        project.apply(plugin: 'findbugs')
        def analyse = project.analyse as AnalysePluginExtension

        def findBugsTask = project.tasks.create('findBugsTask', FindBugs) {
            ignoreFailures = analyse.ignoreFailures
            excludeFilter = project.file(analyse.findbugsConfig)
            classpath = project.files()
            classes = project.fileTree('build/intermediates/classes/')
            effort = 'max'

            source = project.fileTree('src')
            include '**/*.java'
            exclude '**/gen/**'

            reports {
                xml {
                    enabled = false
                    destination "${analyse.findbugsReportPath}/FindBugs.xml"
                    xml.withMessages true
                }
                html {
                    enabled = true
                    destination "${analyse.findbugsReportPath}/FindBugs.html"
                }
            }
        }

        findBugsTask.execute()
    }
}