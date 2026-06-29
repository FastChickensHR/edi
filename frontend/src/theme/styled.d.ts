import 'styled-components'
import type { AppTheme } from './index'

declare module 'styled-components' {
  export interface DefaultTheme extends AppTheme {}
}
