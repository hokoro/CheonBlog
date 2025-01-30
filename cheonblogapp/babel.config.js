module.exports = {
    presets: [
        "@babel/preset-env",   // 최신 JavaScript 문법 변환
        "@babel/preset-react"  // JSX 문법 변환
    ],
    plugins: [
        "@babel/plugin-transform-react-jsx"  // JSX 변환 플러그인 추가
    ]
};