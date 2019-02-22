//
//  Lumberjack.swift
//  TicTacToe
//
//  Created by ismael zavala on 2/19/19.
//  Copyright © 2019 ismael zavala. All rights reserved.
//

import Foundation
import CocoaLumberjack
/// Logging
let log = Lumberjack.self

/// Normal mode
/// console: error, warning, info
/// log files: error, warning, info
/// - 5 x 1 MB log files

/// Debug mode
/// console: error, warning, info, debug, verbose
/// log files: error, warning, info, debug
/// - 20 x 5 MB log files

struct Lumberjack {
    // swiftlint:disable nesting
    private struct Constant {
        // from DDFileLogger.h
        // kDDDefaultLogMaxFileSize      = 1 MB
        // kDDDefaultLogRollingFrequency = 24 Hours
        // kDDDefaultLogMaxNumLogFiles   = 5 Files
        // kDDDefaultLogFilesDiskQuota   = 20 MB
        struct DebugMode {
            static let maxNumLogFiles: UInt = 20
            static let filesDiskQuota: UInt64 = 100 * 1024 * 1024 // 100 MB
            static let maxFileSize: UInt64 = 5 * 1024 * 1024 // 5 MB
        }
    }
    
    static var sortedLogFileInfos: [DDLogFileInfo] {
        let fileLoggers = DDLog.allLoggers.flatMap({ $0 as? DDFileLogger })
        return fileLoggers.flatMap { $0.logFileManager.sortedLogFileInfos }
    }
    
    static func error(_ message: @autoclosure () -> String?,
                      file: StaticString = #file,
                      function: StaticString = #function,
                      line: UInt = #line) {
        swift(message, .error, file, function, line, true)
    }
    static func warning(_ message: @autoclosure () -> String?,
                        file: StaticString = #file,
                        function: StaticString = #function,
                        line: UInt = #line) {
        swift(message, .warning, file, function, line, true)
    }
    static func info(_ message: @autoclosure () -> String?,
                     file: StaticString = #file,
                     function: StaticString = #function,
                     line: UInt = #line) {
        swift(message, .info, file, function, line, true)
    }
    static func debug(_ message: @autoclosure () -> String?,
                      file: StaticString = #file,
                      function: StaticString = #function,
                      line: UInt = #line) {
        swift(message, .debug, file, function, line, true)
    }
    static func verbose(_ message: @autoclosure () -> String?,
                        file: StaticString = #file,
                        function: StaticString = #function,
                        line: UInt = #line) {
        swift(message, .verbose, file, function, line, true)
    }
    static func objc(message: String, flag: DDLogFlag, file: String,
                     function: String, line: UInt, asynchronous: Bool) {
        _log(DDLogMessage(message: message, level: DDLogLevel.verbose,
                          flag: flag, context: 0, file: file, function: function,
                          line: line, tag: nil, options: [.copyFile, .copyFunction],
                          timestamp: nil), asynchronous)
    }
    
    fileprivate static func swift(_ message: @autoclosure () -> String?, _ flag: DDLogFlag, _ file: StaticString,
                                  _ function: StaticString, _ line: UInt, _ asynchronous: Bool) {
        _log(DDLogMessage(message: message() ?? "nil", level: DDLogLevel.verbose,
                          flag: flag, context: 0, file: String(describing: file),
                          function: String(describing: function), line: line,
                          tag: nil, options: [.copyFile, .copyFunction],
                          timestamp: nil), asynchronous)
    }
    
    fileprivate static func _log(_ logMessage: DDLogMessage, _ asynchronous: Bool) {
        defer {
            DDLog.sharedInstance.log(asynchronous: asynchronous, message: logMessage)
        }
        guard modeChanged else { return }
        modeChanged = false
        addConsole()
        addFile()
    }
    
    fileprivate static func addConsole() {
        // remove old
        if let consoleLogger = DDLog.allLoggers.filter({ $0 is DDTTYLogger }).first {
            DDLog.remove(consoleLogger)
        }
        // add new
        add(DDTTYLogger.sharedInstance, .verbose, .verbose)
        DDTTYLogger.sharedInstance.logFormatter = LumberjackFormatter(useEmojis: true)
    }
    
    fileprivate static func addFile() {
        // documents folder for itunes file sharing
        guard let url = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first,
            let manager = DDLogFileManagerDefault(logsDirectory: url.path) else { return }
        // customize
        manager.maximumNumberOfLogFiles = kDDDefaultLogMaxNumLogFiles
        manager.logFilesDiskQuota = kDDDefaultLogFilesDiskQuota
        guard let logger = DDFileLogger(logFileManager: manager) else { return }
        logger.maximumFileSize = kDDDefaultLogMaxFileSize
        // remove old
        if let fileLogger = DDLog.allLoggers.filter({ $0 is DDFileLogger }).first {
            DDLog.remove(fileLogger)
        }
        // add new
        add(logger, .info, .debug)
        logger.logFormatter = LumberjackFormatter(useEmojis: false)
        print("log files at \(url.path)")
    }
    
    fileprivate static func add(_ logger: DDAbstractLogger, _ normalLevel: DDLogLevel, _ debugModeLevel: DDLogLevel) {
        DDLog.add(logger, with: normalLevel)
    }
}

private var debugMode = false
private var modeChanged = true

private class LumberjackFormatter: NSObject, DDLogFormatter {
    let useEmojis: Bool
    init(useEmojis: Bool) {
        self.useEmojis = useEmojis
        super.init()
    }
    func format(message logMessage: DDLogMessage) -> String? {
        let components: [String] = [
            prefix(flag: logMessage.flag),
            "\(logMessage.timestamp)",
            "\(logMessage.fileName)(\(logMessage.line.description))\(suffix(logMessage.function))",
            "\(logMessage.queueLabel)(\(logMessage.threadID))",
            logMessage.message]
        return components.joined(separator: " | ")
    }
    private func prefix(flag: DDLogFlag) -> String {
        if flag.contains(.error) {
            return useEmojis ? "❤️ ERROR" : "E"
        } else if flag.contains(.warning) {
            return useEmojis ? "💛 WARNING" : "W"
        } else if flag.contains(.info) {
            return useEmojis ? "💙 INFO" : "I"
        } else if flag.contains(.debug) {
            return useEmojis ? "💚 DEBUG" : "D"
        } else {
            return useEmojis ? "💜 VERBOSE" : "V"
        }
    }
    private func suffix(_ name: String!) -> String {
        guard let unwrapped = name as String? else { return "" }
        return ".\(unwrapped)"
    }
}
