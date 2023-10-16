// asyne/await를 같이 사용하면 비동기 처리를 동기화된 코드처럼 작성할 수 있다.
// async는 함수 선언 시에 사용 => 해당 함수가 비동기 처리를 위한 함수라는 것을 명시
// await는 async 함수 내에서 비동기 호출하는 부분에 사용
async function get1(bno) {
    // -------------- 연습 --------------
    // 서버로 GET 요청을 보내고 응답 데이터를 result 변수에 저장
    const result = await axios.get(`/replies/list/${bno}`)
    // 결과는 콘솔 창의 data에 담겨서 출력 됨
    // console.log(result)

    // 화면에 결과가 필요하다면 Axios의 호출 결과를 반환받아야 함 (Promise에 결과 반환하기)
    // return result.data
    return result

}

// bno: 현재 게시물 번호, page: 페이지 번호, size: 페이지 사이즈
// goLast: 마지막 페이지 호출 여부(새로운 댓글 등록되면 마지막 페이지에 있기 때문에 강제적으로 마지막 댓글 페이지 호출)
async function getList({bno, page, size, goLast}) {

    // 요청의 쿼리 파라미터로 page와 size 값을 전달
    const result = await axios.get(`/replies/list/${bno}`, {params: {page, size}})

    // 마지막 페이지 계산하고 다시 Axios로 호출
    if (goLast) {
        const total = result.data.total
        const lastPage = parseInt(Math.ceil(total / size))
        return getList({bno:bno, page:lastPage, size:size})
    }

    return result.data

}