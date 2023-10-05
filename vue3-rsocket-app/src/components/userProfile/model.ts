class User {
    id: string | undefined
    age: number | undefined
    gender: Gender | undefined
    time: Date | undefined
}

enum Gender {
    Male = "Male",
    Female = "Female"
}

export {
    User,
    Gender,
}