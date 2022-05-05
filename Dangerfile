github.dismiss_out_of_range_messages

# lint
android_lint.skip_gradle_task = true
android_lint.filtering = true
lint_files = Dir.glob("**/build/reports/lint-results-*.xml")
lint_files.each do |file|
  android_lint.report_file = file
  android_lint.lint(inline_mode: true)
end

# ktlint
checkstyle_format.base_path = Dir.pwd
ktlint_files = Dir.glob("**/build/reports/ktlint/**/*.xml")
ktlint_files.each do |file|
  checkstyle_format.report file
end
