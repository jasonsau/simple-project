export interface PageContent<T> {
    content: T[],
    page: Page
}

interface Page {
    number: number,
    size: number,
    totalElements: number,
    totalPages: number
}